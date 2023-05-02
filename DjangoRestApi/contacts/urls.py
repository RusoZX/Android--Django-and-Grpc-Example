from rest_framework import routers
from .api import ContactViewSet

router = routers.DefaultRouter()

router.register('api/contacts',ContactViewSet, 'contacts')

urlpatterns = router.urls