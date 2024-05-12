using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class ButtonClickHandler : MonoBehaviour
{
    public GetQuizDifficulty quizDifficulty;
    public TextMeshProUGUI resultText;

    private Button button;

    private void Start()
    {
        button = GetComponent<Button>();
        button.onClick.AddListener(OnButtonClicked);
    }

    private void OnButtonClicked()
    {
        quizDifficulty.FetchData();
        UpdateResultText();
    }

    private void UpdateResultText()
    {
        resultText.text = quizDifficulty.resultText.text;
    }
}