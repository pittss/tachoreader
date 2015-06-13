package com.thingtrack.workbench.view.vehicle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.tepi.filtertable.FilterTable;
import org.tepi.filtertable.datefilter.DateInterval;

import ru.xpoft.vaadin.VaadinView;

import com.google.common.eventbus.Subscribe;
import com.thingtrack.tachoreader.domain.Vehicle;
import com.thingtrack.tachoreader.service.api.VehicleService;
import com.thingtrack.workbench.WorkbenchUI;
import com.thingtrack.workbench.component.AbstractI18NView;
import com.thingtrack.workbench.component.ConfirmForm;
import com.thingtrack.workbench.component.PaginationToolbar;
import com.thingtrack.workbench.component.PaginationToolbar.ClickChangePageSizeListener;
import com.thingtrack.workbench.component.PaginationToolbar.ClickFirstPageListener;
import com.thingtrack.workbench.component.PaginationToolbar.ClickLastPageListener;
import com.thingtrack.workbench.component.PaginationToolbar.ClickNextPageListener;
import com.thingtrack.workbench.component.PaginationToolbar.ClickPreviousPageListener;
import com.thingtrack.workbench.component.PaginationToolbar.PageEvent;
import com.thingtrack.workbench.component.Toolbar;
import com.thingtrack.workbench.component.Toolbar.ClickAddListener;
import com.thingtrack.workbench.component.Toolbar.ClickFilterListener;
import com.thingtrack.workbench.component.Toolbar.ClickRefreshListener;
import com.thingtrack.workbench.component.Toolbar.ClickRemoveListener;
import com.thingtrack.workbench.component.Toolbar.ClickSaveListener;
import com.thingtrack.workbench.component.Toolbar.ClickToolbarEvent;
import com.thingtrack.workbench.component.WindowForm;
import com.thingtrack.workbench.event.DashboardEvent.ProfileUpdatedEvent;
import com.thingtrack.workbench.event.DashboardEventBus;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomTable.RowHeaderMode;
import com.vaadin.ui.Field;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("prototype")
@VaadinView(value=VehicleView.NAME, cached=true)
public class VehicleView extends AbstractI18NView implements View, 
			ClickAddListener, ClickSaveListener, ClickRemoveListener,
			ClickRefreshListener,
			ClickChangePageSizeListener, ClickFirstPageListener, ClickPreviousPageListener, 
			ClickNextPageListener, ClickLastPageListener, ClickFilterListener {
	
	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;

	@AutoGenerated
	private PaginationToolbar vehiclePaginationToolbar;

	@AutoGenerated
	private FilterTable vehicleTable;

	@AutoGenerated
	private Toolbar vehicleToolbar;

	private static final long serialVersionUID = 1L;
	public static final String NAME = "vehicles";
	
	@Autowired
	private VehicleService vehicleService;
	
	private List<Vehicle> vehicles = null;
	private BeanItemContainer<Vehicle> vehicleContainer = new BeanItemContainer<Vehicle>(Vehicle.class);
		
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public VehicleView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here		
		initialize();				
	}
	
	private void initialize() {
		// configure table
		vehicleTable.setFilterBarVisible(true);
		vehicleTable.setSelectable(true);
		vehicleTable.setImmediate(true);
		vehicleTable.setMultiSelect(false);
		vehicleTable.setNullSelectionAllowed(false);
		vehicleTable.setColumnReorderingAllowed(true); 
		vehicleTable.setColumnCollapsingAllowed(true);				
		vehicleTable.setRowHeaderMode(RowHeaderMode.INDEX);
		
		// configure filter table on demand
		vehicleTable.setFilterOnDemand(true);
		
		// define toolbar events
		vehicleToolbar.addAddListener(this);
		vehicleToolbar.addSaveListener(this);
		vehicleToolbar.addRemoveListener(this);
		vehicleToolbar.addRefreshListener(this);
		vehicleToolbar.addFilterListener(this);
		
		// define pagination toolbar events
		vehiclePaginationToolbar.addClickChangePageSizeListener(this);
		vehiclePaginationToolbar.addClickFirstPageListener(this);
		vehiclePaginationToolbar.addClickPreviousPageListener(this);
		vehiclePaginationToolbar.addClickNextPageListener(this);
		vehiclePaginationToolbar.addClickLastPageListener(this);
		
		DashboardEventBus.register(this);
	}
	
	@Override
    public void enter(ViewChangeEvent event) {
    }
	
	@SuppressWarnings("serial")
	@PostConstruct
    public void PostConstruct() {        	
    	if (vehicleService != null) {    		
    		try {    	
    			// query filter entities
    			loadDatasource(vehiclePaginationToolbar.getPageNumber(), vehiclePaginationToolbar.getPageSize());
    			    			
    			// set table datasource and configure visible columns
    			vehicleTable.setContainerDataSource(vehicleContainer);    		
				    			
    			vehicleTable.setVisibleColumns((Object[]) new String[] { "id", "registration", "description", "active", "creationDate" } );
    			vehicleTable.setColumnHeaders(new String[] { "ID", "Registration", "Description", "Active", "Creation Date" } );
				
    			// configure collapsed columns
    			vehicleTable.setColumnCollapsed("id", true);				    			

    			// format table columns
    			vehicleTable.setConverter("creationDate", new StringToDateConverter() {
    			    @Override
    			    protected DateFormat getFormat(Locale locale) {   			        
    			        return new SimpleDateFormat("dd/MM/yyyy HH:mm");
    			    }
    			});
    			vehicleTable.setEditable(true);
    			vehicleTable.setTableFieldFactory(new TableFieldFactory() {					
					@Override
					public Field<?> createField(Container container, Object itemId,
							Object propertyId, com.vaadin.ui.Component uiContext) {
						if("active".equals(propertyId))  {
							CheckBox field = new CheckBox();
							field.setReadOnly(true);
							
							return field;
						}
						return null;
					}
				});
    			
    			vehicleToolbar.setTable(vehicleTable);		
    			vehicleToolbar.setDownloadFileName("Vehicles");
    			
    			// intialize table
    			if (vehicleContainer.size() > 0)
    				vehicleTable.select(vehicleContainer.getIdByIndex(0));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
	
	@Subscribe
    public void updateUserName(final ProfileUpdatedEvent event) {
		if (event != null) {
			refreshButtonClick(null);
		}		
    }
	
	@Override
	public void addButtonClick(ClickToolbarEvent event) {
		// created new entity
		Vehicle vehicle = vehicleService.createNewEntity(WorkbenchUI.getCurrent().getUser());
		
		try {
			@SuppressWarnings({ "unused", "serial" })
			WindowForm<Vehicle> vehicleWindow = new WindowForm<Vehicle>(getI18N().getMessage("com.thingtrack.workbench.view.vehicle.VehicleView.tittle.add"), getI18N(), vehicle, new VehicleForm(), new WindowForm.CloseWindowDialogListener<Vehicle>() {
				@Override
				public void windowDialogClose(WindowForm<Vehicle>.CloseWindowDialogEvent<Vehicle> event) {					
					if (event.getDialogResult() != WindowForm.DialogResult.OK)															
			    		return;					    		
										
					try {	
						// insert entity
						Vehicle vehicle = vehicleService.save(event.getDomainEntity());
						
						// refresh table
						vehicleContainer.addBean(vehicle);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void saveButtonClick(ClickToolbarEvent event) {
		// get selected entity
		Vehicle vehicle = (Vehicle) vehicleTable.getValue();
		
		try {
			@SuppressWarnings({ "unused", "serial" })
			WindowForm<Vehicle> vehicleWindow = new WindowForm<Vehicle>(getI18N().getMessage("com.thingtrack.workbench.view.vehicle.VehicleView.tittle.edit"), getI18N(), vehicle, new VehicleForm(), new WindowForm.CloseWindowDialogListener<Vehicle>() {
				@Override
				public void windowDialogClose(WindowForm<Vehicle>.CloseWindowDialogEvent<Vehicle> event) {					
					if (event.getDialogResult() != WindowForm.DialogResult.OK)															
			    		return;					    		
										
					try {
						// update entity
						Vehicle vehicle = (Vehicle) vehicleTable.getValue();
						vehicle.setUpdatedBy(WorkbenchUI.getCurrent().getUser());
						
						vehicle = vehicleService.save(vehicle);
						
						// refresh table
						vehicleTable.refreshRowCache();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	@Override
	public void removeButtonClick(ClickToolbarEvent event) {
		@SuppressWarnings("serial")
		ConfirmForm productConfirmForm = new ConfirmForm(getI18N().getMessage("com.thingtrack.workbench.view.driver.DriverView.tittle.remove"), getI18N().getMessage("com.thingtrack.workbench.view.driver.DriverView.question.remove"), new ConfirmForm.CloseConfirmFormListener() {
			@Override
			public void windowDialogClose(ConfirmForm.CloseWindowDialogEvent event) {						
				if (event.getDialogResult() != ConfirmForm.DialogResult.YES)															
		    		return;	
				
				try {
					// remove entity (unset entity)
					Vehicle vehicle = (Vehicle) vehicleTable.getValue();
					
					vehicle.setActive(false);
					vehicleService.save(vehicle);
					
					// refresh table
					vehicleTable.refreshRowCache();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});	
	}
	
	@Override
	public void refreshButtonClick(ClickToolbarEvent event) {
		// recover entity selected before refresh
		Vehicle vehicleSelected = (Vehicle) vehicleTable.getValue();

		loadDatasource(vehiclePaginationToolbar.getPageNumber(), vehiclePaginationToolbar.getPageSize());
								
		// select register in the grid
		if (vehicleContainer.size() > 0 && vehicleSelected != null)
			vehicleTable.select(vehicleContainer.getItem(vehicleSelected).getBean());
		else if (vehicleContainer.size() > 0)
			vehicleTable.select(vehicleContainer.getIdByIndex(0));		
	}
	
	@Override
	public void addFilterClick(ClickToolbarEvent event) {
		loadDatasource(vehiclePaginationToolbar.getPageNumber(), vehiclePaginationToolbar.getPageSize());
		
	}
	
	@Override
	public void changePageSizeClick(PageEvent event) {
		loadDatasource(event.getPageNumber(), event.getPageSize());
		
	}
	
	@Override
	public void firstPageClick(PageEvent event) {
		loadDatasource(event.getPageNumber(), event.getPageSize());		
	}
	
	@Override
	public void previousPageClick(PageEvent event) {
		loadDatasource(event.getPageNumber(), event.getPageSize());
		
	}
	
	@Override
	public void nextPageClick(PageEvent event) {
		loadDatasource(event.getPageNumber(), event.getPageSize());
		
	}
	
	@Override
	public void lastPageClick(PageEvent event) {
		loadDatasource(event.getPageNumber(), event.getPageSize());
		
	}
		
	private void loadDatasource(int pageNumber, int pageSize) {
		try {
			Integer filterId = null;
			String filterRegistration = null;
			String filterDescription = null;
			Boolean filterActive = null;
			Date filterCreationDateFrom = null;
			Date filterCreationDateTo = null;
			
			if (vehicleTable.getFilterFieldValue("id") != null && !vehicleTable.getFilterFieldValue("id").equals(""))
				filterId = Integer.parseInt(vehicleTable.getFilterFieldValue("id").toString());
			if (vehicleTable.getFilterFieldValue("registration") != null && !vehicleTable.getFilterFieldValue("registration").equals(""))
				filterRegistration = vehicleTable.getFilterFieldValue("registration").toString();
			if (vehicleTable.getFilterFieldValue("description") != null && !vehicleTable.getFilterFieldValue("description").equals(""))
				filterDescription = vehicleTable.getFilterFieldValue("description").toString();
			if (vehicleTable.getFilterFieldValue("active") != null && !vehicleTable.getFilterFieldValue("active").equals(""))
				filterActive = Boolean.parseBoolean(vehicleTable.getFilterFieldValue("active").toString());			
			if (vehicleTable.getFilterFieldValue("creationDate") != null && !vehicleTable.getFilterFieldValue("creationDate").equals("")) {
				DateInterval dateInterval = (DateInterval)vehicleTable.getFilterFieldValue("creationDate");
				
				filterCreationDateFrom = dateInterval.getFrom();
				filterCreationDateTo = dateInterval.getTo();
			}	
			
			// refresh count pages
			vehiclePaginationToolbar.setTotPages(vehicleService.getCount(vehiclePaginationToolbar.getPageSize(), WorkbenchUI.getCurrent().getUser(),
					filterId, filterRegistration, filterDescription, filterActive,
					filterCreationDateFrom, filterCreationDateTo));
			
			// select page registers			
			vehicles = vehicleService.getAll(pageNumber, pageSize, WorkbenchUI.getCurrent().getUser(),
					filterId, filterRegistration, filterDescription, filterActive,
					filterCreationDateFrom, filterCreationDateTo);			

			vehicleContainer.removeAllItems();
			vehicleContainer.addAll(vehicles);
							
			if (vehicleContainer.size() > 0)
				vehicleTable.select(vehicleContainer.getIdByIndex(0));			
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	@Override
	protected void updateLabels() {
		if (getI18N() != null) {
			vehicleTable.setColumnHeaders(new String[] { getI18N().getMessage("com.thingtrack.workbench.view.vehicle.VehicleView.vehicleTable.column.id"),
					  getI18N().getMessage("com.thingtrack.workbench.view.vehicle.VehicleView.vehicleTable.column.registration"),
					  getI18N().getMessage("com.thingtrack.workbench.view.vehicle.VehicleView.vehicleTable.column.description"),
					  getI18N().getMessage("com.thingtrack.workbench.view.vehicle.VehicleView.vehicleTable.column.active"),
					  getI18N().getMessage("com.thingtrack.workbench.view.vehicle.VehicleView.vehicleTable.column.creationDate")
					  });
		}
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// agentToolbar
		vehicleToolbar = new Toolbar();
		vehicleToolbar.setImmediate(false);
		vehicleToolbar.setWidth("-1px");
		vehicleToolbar.setHeight("-1px");
		mainLayout.addComponent(vehicleToolbar);
		
		// agentTable
		vehicleTable = new FilterTable();
		vehicleTable.setImmediate(true);
		vehicleTable.setWidth("100.0%");
		vehicleTable.setHeight("100.0%");
		mainLayout.addComponent(vehicleTable);
		mainLayout.setExpandRatio(vehicleTable, 1.0f);
		
		// agentPaginationToolbar
		vehiclePaginationToolbar = new PaginationToolbar();
		vehiclePaginationToolbar.setImmediate(false);
		vehiclePaginationToolbar.setWidth("-1px");
		vehiclePaginationToolbar.setHeight("-1px");
		mainLayout.addComponent(vehiclePaginationToolbar);
		mainLayout.setComponentAlignment(vehiclePaginationToolbar,
				new Alignment(6));
		
		return mainLayout;
	}
}