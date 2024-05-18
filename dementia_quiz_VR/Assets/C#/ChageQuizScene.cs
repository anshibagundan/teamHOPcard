using UnityEngine;
using UnityEngine.SceneManagement;

public class ChangeQuizScene : MonoBehaviour
{
    private void OnTriggerEnter(Collider other)
    {
        Debug.Log("OnTriggerEnter called"); // デバッグログ追加
        if (other.gameObject.CompareTag("QuizCollider"))
        {
            Debug.Log("QuizCollider detected"); // デバッグログ追加
            SceneManager.LoadScene("QuizScene");
        }
    }
}
