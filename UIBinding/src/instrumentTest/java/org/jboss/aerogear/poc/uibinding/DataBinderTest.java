package org.jboss.aerogear.poc.uibinding;

import android.test.AndroidTestCase;
import android.widget.EditText;

/**
 *
 */
public class DataBinderTest extends AndroidTestCase {

    private Model model;
    private DataBinder<Model> dataBinder;
    private EditText view;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        model = new Model();
        dataBinder = DataBinder.forModel(model);
        view = new EditText(getContext());
        dataBinder.bind(view, "field");
    }

    public void testShouldUpdateModelWithView() {
        // given
        String someText = "bla bla";

        // when
        view.setText(someText);

        // then
        assertEquals("should have updated the model", someText, model.getField());
    }

    public void testShouldUpdateViewWithModel() {
        // given
        String someText = "some other text";

        // when
        Model test = dataBinder.getModel();
        test.setField(someText);

        // then
        assertEquals("view should have been updated", someText, view.getText().toString());
    }

    public static class Model {
        private String field;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }
    }
}
