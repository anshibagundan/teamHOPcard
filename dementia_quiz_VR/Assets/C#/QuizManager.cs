/*
 * 途中のクイズシーンでのクイズデータ通信とカメラ移動
 */
using System;
using UnityEngine;
using UnityEngine.Networking;
using System.Collections;
using TMPro;
using UnityEngine.SceneManagement;

public class QuizManager : MonoBehaviour
{
    public TextMeshProUGUI Quizname;
    public TextMeshProUGUI Quizsel_1;
    public TextMeshProUGUI Quizsel_2;
    public GameObject objectToRotate;
    public float rotationDuration = 1f; // 回転にかける時間
    private bool isRotating = false; // 回転中かどうかのフラグ
    private const string difficultyGetUrl = "https://teamhopcard-aa92d1598b3a.herokuapp.com/quiz-selects/";
    private const string baseGetUrl = "https://teamhopcard-aa92d1598b3a.herokuapp.com/quizzes/";
    private const string posturl = "https://teamhopcard-aa92d1598b3a.herokuapp.com/quiz-tfs/";
    private int difficulty = 1;
    private String geturl = "";
    private int randomIndex = 1;
    private int quizDataId = 1;
    private int[] AskedQuestionList;
    private bool hasnotQuiz = false;
    private bool isfinalQuiz = false;
    private bool fullAskedQuiz = false;

    //シーンが始まるとクイズを取得する
    public void Start()
    {
        StartCoroutine(GetData());
    }

    //ボタンを押すとクイズの正解不正解を登録する
    public void PostQuizTF(String LorR)
    {
        StartCoroutine(PostData(LorR));
        if (!isRotating)
        {
            StartCoroutine(RotateCoroutine(LorR));
        }
    }

    //クイズを取得する関数
    private IEnumerator GetData()
    {
        //クイズの難易度取得
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
                }
                else
                {
                    Debug.LogWarning("No quizdiff found.");
                }
            }
        }

        //クイズの難易度に合わせてURLを指定
        geturl = baseGetUrl + "?difficulty=" + difficulty;

        //出題済みクイズをGet
        using (UnityWebRequest webRequest = UnityWebRequest.Get(posturl))
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

                    if (quizTFDataArray.Length == 2)
                    {
                        AskedQuestionList = new int[2] { quizTFDataArray[0].quiz, quizTFDataArray[1].quiz };
                    }
                    else if (quizTFDataArray.Length == 1)
                    {
                        AskedQuestionList = new int[1] { quizTFDataArray[0].quiz };
                    }
                    else if (quizTFDataArray.Length >= 3)
                    {

                        Debug.LogWarning("Asked Quiz Remained");
                        fullAskedQuiz = true;
                    }
                }
                else
                {
                    Debug.LogWarning("No Askedquiz found.");
                }
            }

        }


        //難易度に合わせてクイズを取得
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
                    int maxAttempts = 30; // 最大試行回数
                    int attempts = 0; // 試行回数のカウンター

                    while (attempts < maxAttempts)
                    {
                        randomIndex = UnityEngine.Random.Range(0, quizDataArray.Length);
                        Quiz QuizData = quizDataArray[randomIndex];
                        quizDataId = quizDataArray[randomIndex].id;

                        if (AskedQuestionList == null || AskedQuestionList.Length == 0)
                        {
                            Quizname.text = QuizData.name;
                            Quizsel_1.text = "1: " + QuizData.sel_1;
                            Quizsel_2.text = "2: " + QuizData.sel_2;
                            break;
                        }
                        else if (AskedQuestionList.Length == 1)
                        {
                            if (AskedQuestionList[0] != quizDataId)
                            {
                                Quizname.text = QuizData.name;
                                Quizsel_1.text = "1: " + QuizData.sel_1;
                                Quizsel_2.text = "2: " + QuizData.sel_2;
                                break;
                            }
                        }
                        else if (AskedQuestionList.Length == 2)
                        {
                            if (AskedQuestionList[0] != quizDataId && AskedQuestionList[1] != quizDataId)
                            {
                                Quizname.text = QuizData.name;
                                Quizsel_1.text = "1: " + QuizData.sel_1;
                                Quizsel_2.text = "2: " + QuizData.sel_2;
                                isfinalQuiz = true;
                                break;
                            }
                        }

                        attempts++; // 試行回数をインクリメント
                    }

                    if (attempts == maxAttempts)
                    {
                        Debug.LogError("Failed to find a quiz that hasn't been asked.");
                        hasnotQuiz = true;
                    }
                }
                else
                {
                    Debug.LogWarning("No quiz found.");
                    hasnotQuiz = true;
                }
            }
        }
    }

    //クイズの正解不正解を送る
    private IEnumerator PostData(String LorR)
    {
        if (!hasnotQuiz)
        {
            //quizのidが奇数なら左が正解に，偶数なら右が正解にする
            WWWForm form = new WWWForm();

            if (quizDataId % 2 == 0)
            {
                if (LorR == "R")
                {
                    form.AddField("cor", "true");
                    form.AddField("quiz", quizDataId);
                }
                else if (LorR == "L")
                {
                    form.AddField("cor", "false");
                    form.AddField("quiz", quizDataId);
                }
            }
            else
            {
                if (LorR == "R")
                {
                    form.AddField("cor", "false");
                    form.AddField("quiz", quizDataId);
                }
                else if (LorR == "L")
                {
                    form.AddField("cor", "true");
                    form.AddField("quiz", quizDataId);
                }
            }

            //ここで正解不正解のデータを送る
            using (UnityWebRequest webRequest = UnityWebRequest.Post(posturl, form))
            {
                webRequest.SetRequestHeader("X-Debug-Mode", "true");
                yield return webRequest.SendWebRequest();

                if (webRequest.result == UnityWebRequest.Result.ConnectionError ||
                    webRequest.result == UnityWebRequest.Result.ProtocolError)
                {
                    Debug.LogError("Error: " + webRequest.error);
                }
            }
        }
        else
        {
            Debug.LogError("no quiz found");
        }
    }
    //回転用
    IEnumerator RotateCoroutine(String LorR)
    {
        isRotating = true;
        Quaternion startRotation = objectToRotate.transform.rotation;
        Quaternion endRotation;

        if (LorR == "R")
        {
            endRotation = startRotation * Quaternion.Euler(0, 90, 0);
        }
        else if(LorR == "L")
        {
            endRotation = startRotation * Quaternion.Euler(0, -90, 0);
        }
        else
        {
            endRotation = startRotation * Quaternion.Euler(0, 0, 0); //おそらくここに到達することはないが...文法エラーを吐いたので
        }

        float elapsedTime = 0;

        while (elapsedTime < rotationDuration)
        {
            objectToRotate.transform.rotation = Quaternion.Slerp(startRotation, endRotation, elapsedTime / rotationDuration);
            elapsedTime += Time.deltaTime;
            yield return null;
        }

        objectToRotate.transform.rotation = endRotation;
        isRotating = false;

        // 回転後にシーンを読み込む
        if (!isfinalQuiz)
        {
            SceneManager.LoadScene("WalkScene");
        }
        else
        {
            SceneManager.LoadScene("TitleScene");
        }
    }
}