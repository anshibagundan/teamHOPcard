using System;
using UnityEngine;
using UnityEngine.Networking;
using System.Collections;
using TMPro;

public class GetQuiz : MonoBehaviour
{
    public TextMeshProUGUI resultText;
    private const string url = "https://teamhopcard-aa92d1598b3a.herokuapp.com/quizzes/";

    public void FetchData()
    {
        StartCoroutine(GetData());
    }

    private IEnumerator GetData()
    {
        using (UnityWebRequest webRequest = UnityWebRequest.Get(url))
        {
            webRequest.SetRequestHeader("X-Debug-Mode", "true");
            yield return webRequest.SendWebRequest();

            if (webRequest.result == UnityWebRequest.Result.ConnectionError || webRequest.result == UnityWebRequest.Result.ProtocolError)
            {
                Debug.LogError("Error: " + webRequest.error);
            }
            else
            {
                string json = webRequest.downloadHandler.text;

                Quiz[] quizDataArray = JsonHelper.FromJson<Quiz>(json);

                if (quizDataArray != null && quizDataArray.Length > 0)
                {
                    Quiz firstQuizData = quizDataArray[1];
                    resultText.text = firstQuizData.name;
                }
                else
                {
                    Debug.LogWarning("No quiz found.");
                }
            }
        }
    }
}

