package threewks.framework.ref;

import com.threewks.thundr.view.json.JsonView;
import threewks.util.RestHelper;

public class ReferenceDataController {

    private final ReferenceDataService referenceDataService;

    public ReferenceDataController(ReferenceDataService referenceDataService) {
        this.referenceDataService = referenceDataService;
    }

    public JsonView getReferenceData() {
        return RestHelper.response(referenceDataService.getReferenceData());
    }

}
