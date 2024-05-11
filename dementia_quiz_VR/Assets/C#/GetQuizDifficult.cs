using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;
using System.Collections;
using TMPro;
using System.Linq;

public class GetQuizDifficulty : MonoBehaviour
{
    public TextMeshProUGUI resultText;
    private const string url = "https://teamhopcard-aa92d1598b3a.herokuapp.com/act-selects/";

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

                QuizData[] quizDataArray = JsonHelper.FromJson<QuizData>(json);

                if (quizDataArray != null && quizDataArray.Length > 0)
                {
                    QuizData firstQuizData = quizDataArray[0];
                    resultText.text = "Difficulty: " + firstQuizData.select_diff.ToString();
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
    public int id;
    public int select_diff;
}

public static class JsonHelper
{
    public static T[] FromJson<T>(string json)
    {
        string newJson = "{ \"array\": " + json + "}";
        Wrapper<T> wrapper = JsonUtility.FromJson<Wrapper<T>>(newJson);
        return wrapper.array;
    }

    [System.Serializable]
    private class Wrapper<T>
    {
        public T[] array;
    }
}