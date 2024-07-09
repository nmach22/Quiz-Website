package main.Servlets;

import main.Manager.DataBaseConnection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;


@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        int duration = Integer.parseInt(request.getParameter("duration"));

        int random = ((String) request.getParameter("random")) == null ? 0 : 1;
        int onePage = ((String) request.getParameter("onePage")) == null ? 0 : 1;
        int immediateCorrection = ((String) request.getParameter("immediateCorrection")) == null ? 0 : 1;
        int practiceMode = ((String) request.getParameter("practiceMode")) == null ? 0 : 1;

        String description = request.getParameter("description");
        String quizTitle = request.getParameter("title");

        String questions = request.getParameter("questions");
        JSONObject obj = new JSONObject(questions);
        JSONArray arr = obj.getJSONArray("questions");

        try {
            int quizId = 0;
            try {
                Connection conn = DataBaseConnection.getConnection();
                String sqlInsertQuizzes = "INSERT INTO quizzes " +
                        "(description, quiz_name, author, is_random, one_page, immediate_correction, practice_mode, duration) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try {
                    PreparedStatement st = conn.prepareStatement(sqlInsertQuizzes, Statement.RETURN_GENERATED_KEYS);
                    st.setString(1, description);
                    st.setString(2, quizTitle);
                    st.setString(3, username);

                    st.setInt(4, random);
                    st.setInt(5, onePage);
                    st.setInt(6, immediateCorrection);
                    st.setInt(7, practiceMode);
                    st.setInt(8, duration);
                    st.executeUpdate();

                    ResultSet rs = st.getGeneratedKeys();
                    if (rs.next()) {
                        quizId = rs.getInt(1);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return;
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                Connection conn = DataBaseConnection.getConnection();
                for (int i = 0; i < arr.length(); i++) {
                    String sqlInsertQuestions = "INSERT INTO questions " +
                            "(quiz_id, question_type, author) " +
                            "VALUES (?, ?, ?)";
                    try {
                        PreparedStatement st = conn.prepareStatement(sqlInsertQuestions, Statement.RETURN_GENERATED_KEYS);
                        st.setInt(1, quizId);

                        JSONObject questionInf = arr.getJSONObject(i);
                        String type = questionInf.getString("type");
                        st.setString(2, type);
                        st.setString(3, username);
                        st.executeUpdate();

                        int questionId = 0;
                        ResultSet rs = st.getGeneratedKeys();
                        if (rs.next()) {
                            questionId = rs.getInt(1);
                        }

                        fillQuestionAndAnswerTables(questionInf, questionId, quizId);

                    } catch (SQLException e) {
                        e.printStackTrace();
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        return;
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect("homePage.jsp?alert=Quiz%20created%20successfully");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.sendRedirect("homePage.jsp?alert=Quiz%20wasn't%20created");
        }
    }

    private void fillQuestionAndAnswerTables(JSONObject questionInf, int questionId, int quizId) {
        addQuestion(questionInf, questionId, quizId);
        addAnswer(questionInf, questionId, quizId);
    }

    private void addQuestion(JSONObject questionInf, int questionId, int quizId) {
        String type = questionInf.getString("type");
        String question = questionInf.getString("question");
        switch (type) {
            case "questionResponse":
                addQuestionInDB(question, questionId, quizId, "questionResponse ");
                break;
            case "questionFillInTheBlank":
                addQuestionInDB(question, questionId, quizId, "questionFillInTheBlank ");
                break;
            case "questionPictureResponse":
                String url = questionInf.getString("url");
                addInPictureQuestion(question, url, questionId, quizId);
                break;
            case "questionMultipleChoice":
                addQuestionInDB(question, questionId, quizId, "questionMultipleChoice ");
                break;
            default:
                System.out.println("Incorrect type");
                break;
        }
    }

    private void addInPictureQuestion(String question, String url, int questionId, int quizId) {
        try {
            Connection conn = DataBaseConnection.getConnection();
            String sqlInsertQuestionResponse = "INSERT INTO questionPictureResponse " +
                    "(question_id, quiz_id, picture_link, question) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sqlInsertQuestionResponse);
            ps.setInt(1, questionId);
            ps.setInt(2, quizId);
            ps.setString(3, url);
            ps.setString(4, question);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void addQuestionInDB(String question, int questionId, int quizId, String DBName) {
        try {
            Connection conn = DataBaseConnection.getConnection();
            String sqlInsertQuestionResponse = "INSERT INTO " + DBName +
                    "(quiz_id, question_id, question) " +
                    "VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sqlInsertQuestionResponse);
            ps.setInt(1, quizId);
            ps.setInt(2, questionId);
            ps.setString(3, question);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void addAnswer(JSONObject questionInf, int questionId, int quizId) {
        String type = questionInf.getString("type");
        String answer = questionInf.getString("answer");
        switch (type) {
            case "questionResponse":
                addAnswersInDB(answer, questionId, quizId, "questionResponseAnswers");
                break;
            case "questionFillInTheBlank":
                addAnswersInDB(answer, questionId, quizId, "questionFillInTheBlankAnswers");
                break;
            case "questionPictureResponse":
                addAnswersInDB(answer, questionId, quizId, "questionPictureResponseAnswers");
                break;
            case "questionMultipleChoice":
                addAnswersInMultipleChoice(questionInf, questionId, quizId, 1);
                break;
            default:
                System.out.println("Incorrect type");
                break;
        }
    }

    private void addAnswersInMultipleChoice(JSONObject questionInf, int questionId, int quizId, int numOfAnswers) {
        try {
            Connection conn = DataBaseConnection.getConnection();
            String sqlInsertQuestionResponse = "INSERT INTO questionMultipleChoiceResponseAnswers " +
                    "(quiz_id, question_id, answer, is_correct) " +
                    "VALUES (?, ?, ?, ?)";

            ArrayList<String> possibleAnswers = new ArrayList<>();
            for (String key : questionInf.keySet()) {
                String keyLower = key.toLowerCase();
                if (keyLower.contains("possible answer")) {
                    possibleAnswers.add(questionInf.getString(key));
                }
            }

            String correctAns = questionInf.getString("answer");
            for (int i = 0; i < possibleAnswers.size(); i++) {
                PreparedStatement st = conn.prepareStatement(sqlInsertQuestionResponse);
                st.setInt(1, quizId);
                st.setInt(2, questionId);
                st.setString(3, possibleAnswers.get(i));
                int tynyInt = correctAns.equals(possibleAnswers.get(i)) ? 1 : 0;
                st.setInt(4, tynyInt);
                if (tynyInt == 1) {
                    numOfAnswers --;
                }
                st.executeUpdate();
            }

            if (numOfAnswers != 0) {
                System.out.println("There are too many correct answers or there is no correct answer at all");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void addAnswersInDB(String answer, int questionId, int quizId, String DBName) {
        try {
            Connection conn = DataBaseConnection.getConnection();
            String sqlInsertQuestionResponse = "INSERT INTO " + DBName +
                    " (question_id, quiz_id, answer) " +
                    "VALUES (?, ?, ?)";

            PreparedStatement st = conn.prepareStatement(sqlInsertQuestionResponse);
            st.setInt(1, questionId);
            st.setInt(2, quizId);
            st.setString(3, answer);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
