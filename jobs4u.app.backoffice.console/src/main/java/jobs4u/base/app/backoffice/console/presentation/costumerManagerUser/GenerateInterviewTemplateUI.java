package jobs4u.base.app.backoffice.console.presentation.costumerManagerUser;


import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.lprog.InterviewGenerator.application.GenerateInterviewTemplateController;
import jobs4u.base.lprog.InterviewGenerator.utils.InterviewQuestionsType;

import java.util.LinkedHashMap;
import java.util.Map;

public class GenerateInterviewTemplateUI extends AbstractUI {

    Map<String, Map<String, String>> questions = new LinkedHashMap<>();

    private final GenerateInterviewTemplateController controller = new GenerateInterviewTemplateController();

    @Override
    protected boolean doShow() {
        System.out.println("Which name do you want to give to the interview template?");
        String templateName = Console.readLine("Template name:");
        System.out.println("Add question to the interview template?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        int option = Console.readInteger("Please select an option:");
        switch (option) {
            case 1:
                boolean flag = true;
                int nrQuestao = 1;
                while(flag) {
                    String questionSelected = requestInterviewQuestionType();
                    String questionFormated =questionSelected+": ("+nrQuestao+")";
                    questions.put(questionFormated, new LinkedHashMap<>());

                    if (questionSelected.equals(InterviewQuestionsType.MULTIPLE_CHOICE.getLabel())) {
                        String question = Console.readLine("Question:");


                        System.out.println("Please enter the options:");
                        int numOptions = Console.readInteger("Number of options:");
                        StringBuilder options = new StringBuilder();
                        for (int i = 0; i < numOptions; i++) {
                            char option1 = (char) ('a' + i);
                            String optionText = Console.readLine("Option " + option1 + ":");
                            options.append(option1).append(") ").append(optionText).append("\n");
                        }

                        questions.get(questionFormated).put("Question", question + "\n" + options.toString());

                    } else {
                        questions.get(questionFormated).put("Question", Console.readLine("Question:"));
                    }
                    questions.get(questionFormated).put("Score", Console.readLine("Score:"));
                    questions.get(questionFormated).put("Correct answer", Console.readLine("Answer:"));
                    nrQuestao++;
                    System.out.println("Do you want to add more questions?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    int option2 = Console.readInteger("Please select an option:");
                    if (option2 == 2) {
                        flag = false;
                    }
                }
                controller.generateInterviewTemplate(questions, templateName);
                break;
            case 2:
                System.out.println("Returning to the main menu.");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                return doShow();
        }

        return true;
    }


    @Override
    public String headline() {
        return "Generate Interview Template";

    }


    private String requestInterviewQuestionType() {
        System.out.println("Please select the type of question you want to add to the interview template:");

        // Exibir opções dinamicamente com base no enum QuestionType
        for (InterviewQuestionsType type : InterviewQuestionsType.values()) {
            System.out.println((type.ordinal() + 1) + ". " + type.getLabel());
        }

        int option = Console.readInteger("Please select an option:");
        InterviewQuestionsType selectedType = InterviewQuestionsType.getByOrdinal(option);
        if (selectedType != null) {
            return selectedType.getLabel();
        } else {
            System.out.println("Invalid option. Please try again.");
            return requestInterviewQuestionType();

        }
    }


}

