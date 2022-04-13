package com.framework.page.site;

public enum MenuOption {

    ADMIN("Admin", "menu_admin_viewAdminModule"), USER_MANAGEMENT("User Management", "menu_admin_UserManagement"),
    USERS("Users", "menu_admin_viewSystemUsers"), PIM("PIM", "menu_pim_viewPimModule"),
    CONFIGURATION("Configuration", "menu_pim_Configuration"), OPTIONAL_FIELDS("Optional Fields", "menu_pim_configurePim"),
    EMPLOYEE_LIST("Employee List", "menu_pim_viewEmployeeList"), LEAVE("Leave", "menu_leave_viewLeaveModule"),
    TIME("Time", "menu_time_viewTimeModule"), RECRUITMENT("Recruitment", "menu_recruitment_viewRecruitmentModule"),
    MY_INFO("My Info", "menu_pim_viewMyDetails"), PERFORMANCE("Performance", "menu__Performance"),
    DASHBOARD("Dashboard", "menu_dashboard_index"), DIRECTORY("Directory", "menu_directory_viewDirectory"),
    MAINTENANCE("Maintenance", "menu_maintenance_purgeEmployee"), BUZZ("Buzz", "menu_buzz_viewBuzz");

    private final String stringValue;
    private final String menuId;

    MenuOption(String stringValue, String menuId) {
        this.stringValue = stringValue;
        this.menuId = menuId;
    }

    public String getStringValue() {
        return this.stringValue;
    }

    public String getMenuId() {
        return this.menuId;
    }

}
