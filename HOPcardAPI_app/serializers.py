from rest_framework import serializers
from .models import Quiz, Action, Quiz_select, Act_select, Quiz_TF, Act_TF

class QuizSerializer(serializers.ModelSerializer):
    class Meta:
        model = Quiz
        fields = ['id', 'name', 'difficulty', 'sel_1', 'sel_2']

class ActionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Action
        fields = ['id', 'name', 'difficulty', 'sel_1', 'sel_2']

class QuizSelectSerializer(serializers.ModelSerializer):
    class Meta:
        model = Quiz_select
        fields = ['id', 'select_diff']

class ActSelectSerializer(serializers.ModelSerializer):
    class Meta:
        model = Act_select
        fields = ['id', 'select_diff']

class QuizTFSerializer(serializers.ModelSerializer):
    class Meta:
        model = Quiz_TF
        fields = ['id', 'quiz', 'cor']

class ActTFSerializer(serializers.ModelSerializer):
    class Meta:
        model = Act_TF
        fields = ['id', 'action', 'cor']