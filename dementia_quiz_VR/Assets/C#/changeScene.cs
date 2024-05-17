using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;
using UnityEngine.SceneManagement;

public class changeScene : MonoBehaviour
{
    private const string quiztfurl = "https://teamhopcard-aa92d1598b3a.herokuapp.com/quiz-tfs/";
    private const string acttfurl = "https://teamhopcard-aa92d1598b3a.herokuapp.com/act-tfs/";
    private bool hasQuizTF = false;
    private bool hasActTF = false;
    public void change_button()
    {
        StartCoroutine(GetData());
        if (!hasActTF && !hasQuizTF)
        {
            SceneManager.LoadScene("New_WalkScene");
        }


    }

    private IEnumerator GetData()
    {
        //出題済みクイズQuizをGet
        using (UnityWebRequest webRequest = UnityWebRequest.Get(quiztfurl))
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

                QuizTF[] quizTFDataArray = JsonHelper.FromJson<QuizTF>(json);

                if (quizTFDataArray != null && quizTFDataArray.Length > 0)
                {

                    hasQuizTF = true;
                }
                else
                {
                    Debug.Log("No quizTF found.");
                }
            }

        }
        using (UnityWebRequest webRequest = UnityWebRequest.Get(acttfurl))
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

                ActTF[] ActTFDataArray = JsonHelper.FromJson<ActTF>(json);

                if (ActTFDataArray != null && ActTFDataArray.Length > 0)
                {

                    hasActTF = true;
                }
                else
                {
                    Debug.Log("No quizTF found.");
                }
            }

        }
    }



}
