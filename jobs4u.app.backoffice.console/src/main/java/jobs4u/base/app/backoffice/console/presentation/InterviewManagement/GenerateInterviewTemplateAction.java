package jobs4u.base.app.backoffice.console.presentation.InterviewManagement;

import eapli.framework.actions.Action;

public class GenerateInterviewTemplateAction implements Action {

    @Override
    public boolean execute() {
        return new GenerateInterviewTemplateUI().show();
    }

}
