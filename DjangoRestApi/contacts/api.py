from .models import Contact
from rest_framework import viewsets, permissions
from .serializers import ContactSerializer

class ContactViewSet(viewsets.ModelViewSet):
    queryset=Contact.objects.all()
    permission_classes = [permissions.AllowAny]
    serializer_class = ContactSerializer

    def get_queryset(self):
        queryset=Contact.objects.all()
        quantity = self.request.query_params.get('quantity', None)
        if quantity is not None:
            return Contact.objects.all()[:int(quantity)]
        else:
            return Contact.objects.all()