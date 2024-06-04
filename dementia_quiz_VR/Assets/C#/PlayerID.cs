using UnityEngine;

public class PlayerID : MonoBehaviour
{
    [SerializeField] public int id;
    public GetActDifficulty act;

    private void Start()
    {

        // id = act.GetQuizDifficulty(); // 
    }
}