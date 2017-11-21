package threewks.controller;

import com.threewks.thundr.view.View;
import com.threewks.thundr.view.redirect.RedirectView;
import threewks.service.bootstrap.BootstrapService;

public class BootstrapController {

    private final BootstrapService bootstrapService;

    public BootstrapController(BootstrapService bootstrapService) {
        this.bootstrapService = bootstrapService;
    }

    public View bootstrap() {
        bootstrapService.createSuperUser();

        return new RedirectView("/system/gmail/setup");  // Initialise gmail
    }

}
