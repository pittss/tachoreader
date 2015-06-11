package com.thingtrack.workbench.view.administrator;

import com.thingtrack.tachoreader.domain.Agent;
import com.thingtrack.workbench.component.AbstractI18NValidableCustomComponent;
import com.thingtrack.workbench.component.OrganizationField;
import com.thingtrack.workbench.component.UserField;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class AdministratorForm extends AbstractI18NValidableCustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private TabSheet administratorTabSheet;
	@AutoGenerated
	private VerticalLayout userLayout;
	@AutoGenerated
	@PropertyId("user")
	private UserField userField;
	@AutoGenerated
	private VerticalLayout organizationslLayout;
	@AutoGenerated
	@PropertyId("organizations")
	private OrganizationField organizationField;
	@AutoGenerated
	private VerticalLayout driverLayout;
	@AutoGenerated
	@PropertyId("email")
	private TextField emailField;
	@AutoGenerated
	private HorizontalLayout nameLayout;
	@AutoGenerated
	@PropertyId("active")
	private CheckBox activeField;
	@AutoGenerated
	@PropertyId("name")
	private TextField nameField;
	
	private static final int TAB_ADMINISTRATOR = 0;
	private static final int TAB_ORGANIZATIONS = 1;
	private static final int TAB_USER = 2;
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public AdministratorForm() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here		
    	initialize(); 	    	    	
	}
		
	private void initialize() {
		nameField.setNullSettingAllowed(false);
		nameField.setNullRepresentation("");
		nameField.setImmediate(false);
		nameField.setValidationVisible(true);
		nameField.addValidator(new BeanValidator(Agent.class, "name"));

		emailField.setNullSettingAllowed(false);
		emailField.setNullRepresentation("");
		emailField.setImmediate(false);
		emailField.setValidationVisible(true);
		emailField.addValidator(new BeanValidator(Agent.class, "email"));
		emailField.addValidator(new EmailValidator("Set a correct email"));
		
		nameField.setNullSettingAllowed(true);
		nameField.setNullRepresentation("");				
	}	
	
	@Override
	protected boolean isValidate() {
		boolean errorValidation = false;
		
		if (!nameField.isValid()) {
			nameField.setRequiredError("The name is required");
			errorValidation = true;
		}
		else
			nameField.setRequiredError(null);

		if (!emailField.isValid()) {
			emailField.setRequiredError("The email is required");
			errorValidation = true;
		}
		else
			emailField.setRequiredError(null);
		
		if (organizationField.isValid())
			errorValidation = true;
		
		if (userField.isValid())
			errorValidation = true;
		
		return errorValidation;		
	}
	
	@Override
	protected void updateLabels() {
		administratorTabSheet.getTab(TAB_ADMINISTRATOR).setCaption(getI18N().getMessage("com.thingtrack.workbench.view.administrator.AdministratorForm.administratorTabSheet.tabGeneral.caption"));
		administratorTabSheet.getTab(TAB_ORGANIZATIONS).setCaption(getI18N().getMessage("com.thingtrack.workbench.view.administrator.AdministratorForm.administratorTabSheet.tabOrganizations.caption"));
		administratorTabSheet.getTab(TAB_USER).setCaption(getI18N().getMessage("com.thingtrack.workbench.view.administrator.AdministratorForm.administratorTabSheet.tabUser.caption"));
		nameField.setCaption(getI18N().getMessage("com.thingtrack.workbench.view.administrator.AdministratorForm.nameField.caption"));
		emailField.setCaption(getI18N().getMessage("com.thingtrack.workbench.view.administrator.AdministratorForm.emailField.caption"));
		activeField.setCaption(getI18N().getMessage("com.thingtrack.workbench.view.administrator.AdministratorForm.activeField.caption"));
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("400px");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("400px");
		setHeight("-1px");
		
		// driverTabSheet
		administratorTabSheet = buildDriverTabSheet();
		mainLayout.addComponent(administratorTabSheet);
		mainLayout.setExpandRatio(administratorTabSheet, 1.0f);
		
		return mainLayout;
	}

	@AutoGenerated
	private TabSheet buildDriverTabSheet() {
		// common part: create layout
		administratorTabSheet = new TabSheet();
		administratorTabSheet.setImmediate(true);
		administratorTabSheet.setWidth("100.0%");
		administratorTabSheet.setHeight("-1px");
		
		// driverLayout
		driverLayout = buildDriverLayout();
		administratorTabSheet.addTab(driverLayout, "Driver", null);
		
		// organizationslLayout
		organizationslLayout = buildOrganizationslLayout();
		administratorTabSheet.addTab(organizationslLayout, "Organizations", null);
		
		// userLayout
		userLayout = buildUserLayout();
		administratorTabSheet.addTab(userLayout, "User", null);
		
		return administratorTabSheet;
	}

	@AutoGenerated
	private VerticalLayout buildDriverLayout() {
		// common part: create layout
		driverLayout = new VerticalLayout();
		driverLayout.setImmediate(false);
		driverLayout.setWidth("100.0%");
		driverLayout.setHeight("-1px");
		driverLayout.setMargin(false);
		driverLayout.setSpacing(true);
		
		// nameLayout
		nameLayout = buildNameLayout();
		driverLayout.addComponent(nameLayout);
		
		// emailField
		emailField = new TextField();
		emailField.setCaption("Email");
		emailField.setImmediate(false);
		emailField.setWidth("300px");
		emailField.setHeight("-1px");
		emailField.setRequired(true);
		driverLayout.addComponent(emailField);
		
		return driverLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildNameLayout() {
		// common part: create layout
		nameLayout = new HorizontalLayout();
		nameLayout.setImmediate(false);
		nameLayout.setWidth("100.0%");
		nameLayout.setHeight("-1px");
		nameLayout.setMargin(false);
		nameLayout.setSpacing(true);
		
		// nameField
		nameField = new TextField();
		nameField.setCaption("Name");
		nameField.setImmediate(false);
		nameField.setWidth("300px");
		nameField.setHeight("-1px");
		nameField.setRequired(true);
		nameLayout.addComponent(nameField);
		
		// activeField
		activeField = new CheckBox();
		activeField.setCaption("Active");
		activeField.setImmediate(false);
		activeField.setWidth("-1px");
		activeField.setHeight("-1px");
		activeField.setRequired(true);
		nameLayout.addComponent(activeField);
		nameLayout.setExpandRatio(activeField, 1.0f);
		nameLayout.setComponentAlignment(activeField, new Alignment(10));
		
		return nameLayout;
	}

	@AutoGenerated
	private VerticalLayout buildOrganizationslLayout() {
		// common part: create layout
		organizationslLayout = new VerticalLayout();
		organizationslLayout.setImmediate(false);
		organizationslLayout.setWidth("100.0%");
		organizationslLayout.setHeight("-1px");
		organizationslLayout.setMargin(false);
		
		// organizationField
		organizationField = new OrganizationField();
		organizationField.setImmediate(false);
		organizationField.setWidth("100.0%");
		organizationField.setHeight("200px");
		organizationslLayout.addComponent(organizationField);
		
		return organizationslLayout;
	}

	@AutoGenerated
	private VerticalLayout buildUserLayout() {
		// common part: create layout
		userLayout = new VerticalLayout();
		userLayout.setImmediate(false);
		userLayout.setWidth("100.0%");
		userLayout.setHeight("-1px");
		userLayout.setMargin(false);
		userLayout.setSpacing(true);
		
		// userField
		userField = new UserField();
		userField.setImmediate(false);
		userField.setWidth("100.0%");
		userField.setHeight("-1px");
		userLayout.addComponent(userField);
		
		return userLayout;
	}
}