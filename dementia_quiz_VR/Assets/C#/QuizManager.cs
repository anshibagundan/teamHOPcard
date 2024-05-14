using System;
 using UnityEngine;
 using UnityEngine.Networking;
 using System.Collections;
 using TMPro;

 public class QuizManager : MonoBehaviour
 {
     public TextMeshProUGUI Quizname;
     public TextMeshProUGUI Quizsel_1;
     public TextMeshProUGUI Quizsel_2;
     private const string difficultyGetUrl = "https://teamhopcard-aa92d1598b3a.herokuapp.com/quiz-selects/";
     private const string baseGetUrl = "https://teamhopcard-aa92d1598b3a.herokuapp.com/quizzes/";
     private const string posturl = "https://teamhopcard-aa92d1598b3a.herokuapp.com/quiz-tfs/";
     private int difficulty = 1;
     private String geturl = "";
     private int randomIndex = 1;


     public void Start()
     {
         StartCoroutine(GetData());
     }

     public void PostQuizTF(String LorR)
     {
         StartCoroutine(PostData(LorR));
     }

     private IEnumerator GetData()
     {

         using (UnityWebRequest webRequest = UnityWebRequest.Get(difficultyGetUrl))
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

                 QuizSelDiff[] QuizSelDiffDataArray = JsonHelper.FromJson<QuizSelDiff>(json);

                 if (QuizSelDiffDataArray != null && QuizSelDiffDataArray.Length > 0)
                 {
                     QuizSelDiff QuizSelDiffData = QuizSelDiffDataArray[0];

                     difficulty = QuizSelDiffData.select_diff;
                     Debug.LogWarning(difficulty);

                 }
                 else
                 {
                     Debug.LogWarning("No quiz found.");
                 }
             }
         }

         geturl = baseGetUrl + "?difficulty=" + difficulty;
         Debug.LogWarning(geturl);
         using (UnityWebRequest webRequest = UnityWebRequest.Get(geturl))
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
                     randomIndex = UnityEngine.Random.Range(0, quizDataArray.Length);
                     Quiz QuizData = quizDataArray[randomIndex];
                     Quizname.text = QuizData.name;
                     Quizsel_1.text = "1: " + QuizData.sel_1;
                     Quizsel_2.text = "2: " + QuizData.sel_2;

                 }
                 else
                 {
                     Debug.LogWarning("No quiz found.");
                 }
             }
         }
     }
     //PostDataまでは書いた
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
                    quizDataId = quizDataArray[randomIndex].id;
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