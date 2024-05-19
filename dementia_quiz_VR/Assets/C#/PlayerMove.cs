using System;
using UnityEngine;
using UnityEngine.InputSystem;

[RequireComponent(typeof(Rigidbody))]
public class PlayerMover : MonoBehaviour
{
    [SerializeField] private float _moveSpeed = 5;

    private Rigidbody _rigidbody;
    private InputMove _gameInputs;
    private Vector2 _moveInputValue;


    private void Awake()
    {
        _rigidbody = GetComponent<Rigidbody>();

        // Actionスクリプトのインスタンス生成
        _gameInputs = new InputMove();

        // Actionイベント登録
        _gameInputs.Player.Move.started += OnMove;
        _gameInputs.Player.Move.performed += OnMove;
        _gameInputs.Player.Move.canceled += OnMove;

        // Input Actionを機能させるためには、
        // 有効化する必要がある
        _gameInputs.Enable();
    }

    private void OnDestroy()
    {
        // 自身でインスタンス化したActionクラスはIDisposableを実装しているので、
        // 必ずDisposeする必要がある
        _gameInputs?.Dispose();
    }

    private void OnMove(InputAction.CallbackContext context)
    {
        // Moveアクションの入力取得
        _moveInputValue = context.ReadValue<Vector2>();
    }

    private void FixedUpdate()
    {
        // 移動入力がある場合
        if (_moveInputValue != Vector2.zero)
        {
            // 移動方向のベクトルを計算
            Vector3 moveDirection = new Vector3(
                _moveInputValue.x,
                0,
                _moveInputValue.y
            ).normalized;

            // 目標速度を計算
            Vector3 targetVelocity = moveDirection * _moveSpeed;

            // 目標速度を適用
            _rigidbody.velocity = targetVelocity;
        }
        else
        {
            // 移動入力がない場合、速度を0に設定
            _rigidbody.velocity = Vector3.zero;
        }
    }
}