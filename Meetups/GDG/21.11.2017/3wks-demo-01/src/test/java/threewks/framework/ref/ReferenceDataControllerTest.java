package threewks.framework.ref;

import com.threewks.thundr.view.json.JsonView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static com.atomicleopard.expressive.Expressive.map;
import static org.mockito.Mockito.when;
import static threewks.WebAsserts.assertOk;

@RunWith(MockitoJUnitRunner.class)
public class ReferenceDataControllerTest {

    @Mock
    private ReferenceDataService referenceDataService;
    @InjectMocks
    private ReferenceDataController controller;

    @Test
    public void getReferenceData() {
        Map<String, List<ReferenceDataDto>> expected = map();

        when(referenceDataService.getReferenceData()).thenReturn(expected);

        JsonView result = controller.getReferenceData();

        assertOk(result, expected);
    }

}
