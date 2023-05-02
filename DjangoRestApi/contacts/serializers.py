from rest_framework import serializers
from.models import Contact

class ContactSerializer(serializers.ModelSerializer):
    class Meta:
        model = Contact
        fields = ('id','gender', 'title', 'firstName', 'lastName', 'street', 'province', 'city', 'country', 'postalCode', 'longitudeCoor',
                   'latitudeCoor', 'timeZone', 'timeDesc', 'email', 'userName', 'birthDay','age', 'landLinePhone', 'phoneNumber', 'urlImage', 'created_at' )
        read_only_fields = ('created_at', )