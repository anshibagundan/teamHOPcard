using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class OnTriggerEnter : MonoBehaviour
{
   private void CollisionTrigger(Collider other)
    {
        if (other.gameObject.CompareTag("QuizCollider"))
        {
            SceneManager.LoadScene("QuizScene");
        }
    }
}
