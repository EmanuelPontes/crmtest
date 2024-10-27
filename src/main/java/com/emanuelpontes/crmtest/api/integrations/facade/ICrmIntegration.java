package com.emanuelpontes.crmtest.api.integrations.facade;

import com.emanuelpontes.crmtest.api.integrations.crm.model.CustomerCRM;

public interface ICrmIntegration {
    public void patchCustomer(CustomerCRM customer);
}
