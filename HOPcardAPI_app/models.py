from django.db import models

class Quiz(models.Model):
    name = models.CharField(max_length=100)
    difficulty = models.IntegerField()

class Action(models.Model):
    name = models.CharField(max_length=100)
    difficulty = models.IntegerField()

class Quiz_select(models.Model):
    select_diff = models.IntegerField()

class Act_select(models.Model):
    select_diff = models.IntegerField()

class Quiz_TF(models.Model):
    quiz = models.ForeignKey(Quiz, on_delete=models.CASCADE)
    cor = models.BooleanField()

class Act_TF(models.Model):
    action = models.ForeignKey(Action, on_delete=models.CASCADE)
    cor = models.BooleanField()