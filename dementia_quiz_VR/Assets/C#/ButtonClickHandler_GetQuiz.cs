using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class ButtonClickHandler_GetQuiz : MonoBehaviour
{
    public GetQuiz quiz;
    public TextMeshProUGUI Quizname;
    public TextMeshProUGUI Quizsel_1;
    public TextMeshProUGUI Quizsel_2;


    private Button button;

    private void Start()
    {
        button = GetComponent<Button>();
        button.onClick.AddListener(OnButtonClicked);
    }

    private void OnButtonClicked()
    {
        quiz.FetchData();
        UpdateResultText();
    }

    private void UpdateResultText()
    {
        Quizname.text = quiz.Quizname.text;
        Quizsel_1.text = quiz.Quizsel_1.text;
        Quizsel_2.text = quiz.Quizsel_2.text;

    }
}