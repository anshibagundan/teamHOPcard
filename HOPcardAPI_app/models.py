from django.db import models

class Quiz(models.Model):
    name = models.CharField(max_length=100)
    difficulty = models.IntegerField()
    sel_1 = models.CharField(max_length=100)
    sel_2 = models.CharField(max_length=100)

class Action(models.Model):
    name = models.CharField(max_length=100)
    difficulty = models.IntegerField()
    sel_1 = models.CharField(max_length=100)
    sel_2 = models.CharField(max_length=100)

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
