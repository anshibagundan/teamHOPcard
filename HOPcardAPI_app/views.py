from rest_framework import viewsets
from .models import Quiz, Action, Quiz_select, Act_select, Quiz_TF, Act_TF
from .serializers import QuizSerializer, ActionSerializer, QuizSelectSerializer, ActSelectSerializer, QuizTFSerializer, ActTFSerializer

class QuizViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = Quiz.objects.all()
    serializer_class = QuizSerializer

    def get_queryset(self):
        queryset = super().get_queryset()
        difficulty = self.request.query_params.get('difficulty', None)
        if difficulty is not None:
            queryset = queryset.filter(difficulty=difficulty)
        return queryset

class ActionViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = Action.objects.all()
    serializer_class = ActionSerializer

    def get_queryset(self):
        queryset = super().get_queryset()
        difficulty = self.request.query_params.get('difficulty', None)
        if difficulty is not None:
            queryset = queryset.filter(difficulty=difficulty)
        return queryset

class QuizSelectViewSet(viewsets.ModelViewSet):
    queryset = Quiz_select.objects.all()
    serializer_class = QuizSelectSerializer

class ActSelectViewSet(viewsets.ModelViewSet):
    queryset = Act_select.objects.all()
    serializer_class = ActSelectSerializer

class QuizTFViewSet(viewsets.ModelViewSet):
    queryset = Quiz_TF.objects.all()
    serializer_class = QuizTFSerializer

class ActTFViewSet(viewsets.ModelViewSet):
    queryset = Act_TF.objects.all()
    serializer_class = ActTFSerializer