using System;
using UnityEngine;
using UnityEngine.Networking;
using System.Collections;
using TMPro;

public class PostQuizTF : MonoBehaviour
{
    private const string geturl = "https://teamhopcard-aa92d1598b3a.herokuapp.com/quizzes/";
    private const string posturl = "https://teamhopcard-aa92d1598b3a.herokuapp.com/quiz-tfs/";

    public void FetchData(String LorR)
    {
        StartCoroutine(PostData(LorR));
    }

    private IEnumerator PostData(String LorR)
    {
        int quizDataId = 0;

        using (UnityWebRequest webRequest = UnityWebRequest.Get(geturl))
        {
            webRequest.SetRequestHeader("X-Debug-Mode", "true");
            yield return webRequest.SendWebRequest();

            if (webRequest.result == UnityWebRequest.Result.ConnectionError || webRequest.result == UnityWebRequest.Result.ProtocolError)
            {
                Debug.LogError("Error: " + webRequest.error);
                yield break;
            }
            else
            {
                string json = webRequest.downloadHandler.text;

                Quiz[] quizDataArray = JsonHelper.FromJson<Quiz>(json);

                if (quizDataArray != null && quizDataArray.Length > 0)
                {
                    quizDataId = quizDataArray[1].id;
                }
                else
                {
                    Debug.LogWarning("No quiz found.");
                    yield break;
                }
            }
        }


        WWWForm form = new WWWForm();


        if (quizDataId % 2 == 0)
        {
            if (LorR == "R")
            {
                form.AddField("cor", "true");
                form.AddField("quiz", quizDataId);
            }
            else if(LorR == "L")
            {
                form.AddField("cor", "false");
                form.AddField("quiz", quizDataId);
            }
        }else{
            if (LorR == "R")
            {
                form.AddField("cor", "false");
                form.AddField("quiz", quizDataId);
            }
            else if(LorR == "L")
            {
                form.AddField("cor", "true");
                form.AddField("quiz", quizDataId);
            }
        }

        using (UnityWebRequest webRequest = UnityWebRequest.Post(posturl, form))
        {
            webRequest.SetRequestHeader("X-Debug-Mode", "true");
            yield return webRequest.SendWebRequest();

            if (webRequest.result == UnityWebRequest.Result.ConnectionError || webRequest.result == UnityWebRequest.Result.ProtocolError)
            {
                Debug.LogError("Error: " + webRequest.error);
            }
        }
    }
}