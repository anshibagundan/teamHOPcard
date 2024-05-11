using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;
using System.Collections;
using JetBrains.Annotations;
using TMPro;

public class GetQuizDifficulty : MonoBehaviour
{
    [CanBeNull] public TextMeshProUGUI resultText;
    
    private const string url = "https://teamhopcard-aa92d1598b3a.herokuapp.com/quiz-selects/";

    public void FetchData()
    {
        StartCoroutine(GetData());
    }

    private IEnumerator GetData()
    {
        using (UnityWebRequest webRequest = UnityWebRequest.Get(url))
        {
            yield return webRequest.SendWebRequest();

            if (webRequest.result == UnityWebRequest.Result.ConnectionError || webRequest.result == UnityWebRequest.Result.ProtocolError)
            {
                Debug.LogError("Error: " + webRequest.error);
            }
            else
            {
                string json = webRequest.downloadHandler.text;
                QuizData[] quizDataArray = JsonUtility.FromJson<QuizData[]>(json);

                if (quizDataArray != null && quizDataArray.Length > 0)
                {
                    QuizData firstQuizData = quizDataArray[0];
                    resultText.text = firstQuizData.question;
                }
                else
                {
                    Debug.LogWarning("No quiz data found.");
                }
            }
        }
    }
}

[System.Serializable]
public class QuizData
{
    public string question;
    // 他のプロパティを追加できます
}