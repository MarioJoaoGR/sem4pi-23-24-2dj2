package jobs4u.integration.plugins.Programador2AnosExperienciaRequirement.Utils;


import java.util.ArrayList;
import java.util.List;

public class PluginQuestions {

    private List<Question> questions = new ArrayList<>();

    public PluginQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
