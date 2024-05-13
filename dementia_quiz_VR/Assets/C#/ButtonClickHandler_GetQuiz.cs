using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class ButtonClickHandler_GetQuiz : MonoBehaviour
{
    public GetQuiz quiz;
    public TextMeshProUGUI resultText;

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
        resultText.text = quiz.resultText.text;
    }
}