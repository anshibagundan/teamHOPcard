/*
 * 最後ののクイズシーンでのクイズデータ通信
 */
using System;
using UnityEngine;
using UnityEngine.Networking;
using System.Collections;
using TMPro;
using UnityEngine.SceneManagement;

public class ActionManager : MonoBehaviour
{
    public TextMeshProUGUI Actname;
    public TextMeshProUGUI Actsel_1;
    public TextMeshProUGUI Actsel_2;
    private const string difficultyGetUrl = "https://teamhopcard-aa92d1598b3a.herokuapp.com/act-selects/";
    private const string baseGetUrl = "https://teamhopcard-aa92d1598b3a.herokuapp.com/actions/";
    private const string posturl = "https://teamhopcard-aa92d1598b3a.herokuapp.com/act-tfs/";
    private int difficulty = 1;
    private String geturl = "";
    private int randomIndex = 1;
    private int ActDataId = 1;
    private bool hasnotAct = false;
    private bool isfinalAct = false;
    private bool fullAskedAct = false;
    private Quaternion endRotation;

    //シーンが始まるとクイズを取得する
    public void Start()
    {
        StartCoroutine(GetData());
    }

    //ボタンを押すとクイズの正解不正解を登録する
    public void PostActTF(String LorR)
    {
        StartCoroutine(PostData(LorR));
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

                ActSelDiff[] ActSelDiffDataArray = JsonHelper.FromJson<ActSelDiff>(json);

                if (ActSelDiffDataArray != null && ActSelDiffDataArray.Length > 0)
                {
                    ActSelDiff ActSelDiffData = ActSelDiffDataArray[0];

                    difficulty = ActSelDiffData.select_diff;
                }
                else
                {
                    Debug.LogWarning("No Actdiff found.");
                }
            }
        }

        //クイズの難易度に合わせてURLを指定
        geturl = baseGetUrl + "?difficulty=" + difficulty;

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

                Action[] ActDataArray = JsonHelper.FromJson<Action>(json);

                if (ActDataArray != null && ActDataArray.Length > 0)
                {

                    //WalkSceneで表示するものと同じにする修正あり
                    randomIndex = UnityEngine.Random.Range(0, ActDataArray.Length);

                    Action ActData = ActDataArray[randomIndex];
                    ActDataId = ActDataArray[randomIndex].id;

                    Actname.text = ActData.name;
                    Actsel_1.text = "1: " + ActData.sel_1;
                    Actsel_2.text = "2: " + ActData.sel_2;
                }
                else
                {
                    Debug.LogWarning("No Act found.");
                    hasnotAct = true;
                }
            }
        }
    }

    //クイズの正解不正解を送る
    private IEnumerator PostData(String LorR)
    {
        if (!hasnotAct)
        {
            //Actのidが奇数なら左が正解に，偶数なら右が正解にする
            WWWForm form = new WWWForm();

            if (ActDataId % 2 == 0)
            {
                if (LorR == "R")
                {
                    form.AddField("cor", "true");
                    form.AddField("action", ActDataId);
                }
                else if (LorR == "L")
                {
                    form.AddField("cor", "false");
                    form.AddField("action", ActDataId);
                }
            }
            else
            {
                if (LorR == "R")
                {
                    form.AddField("cor", "false");
                    form.AddField("action", ActDataId);
                }
                else if (LorR == "L")
                {
                    form.AddField("cor", "true");
                    form.AddField("action", ActDataId);
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
            Debug.LogError("no Act found");
        }

        SceneManager.LoadScene("TitleScene");
    }
}