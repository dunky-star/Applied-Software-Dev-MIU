
# ----------------------
# VARIABLES
# ----------------------
IMAGE_NAME=dunky2012/hotel-booking-backend
IMAGE_TAG=latest
PLATFORMS=linux/amd64,linux/arm64

NAMESPACE=hotel-booking-system
KUBECTL=kubectl

ALL_MANIFESTS=\
  hotel-booking-api-config.yaml \
  hotel-booking-api-secret.yaml \
  hotel-booking-api-deployment.yaml \
  hotel-booking-service.yaml \
  hotel-booking-postgres-secret.yaml \
  postgres-service.yaml \
  postgres-statefulset.yaml \
  postgres-pv.yaml \
  hotel-booking-nginx-ingress.yaml \
  hotel-booking-system-hpa.yaml \
  metallb-config.yaml

DELETE_SAFE_MANIFESTS=\
  hotel-booking-api-config.yaml \
  hotel-booking-api-secret.yaml \
  hotel-booking-api-deployment.yaml \
  hotel-booking-service.yaml \
  hotel-booking-postgres-secret.yaml \
  postgres-service.yaml \
  hotel-booking-system-hpa.yaml \
  metallb-config.yaml

DEPLOYMENT_MANIFEST=hotel-booking-api-deployment.yaml

.PHONY: build push run all delete-deployment reapply-deployment apply delete logs exec describe port-forward restart hpa ingress endpoints get


# ----------------------
# TARGETS
# ----------------------
build:
	docker buildx build --platform $(PLATFORMS) -t $(IMAGE_NAME):$(IMAGE_TAG) .

push:
	docker buildx build --platform $(PLATFORMS) -t $(IMAGE_NAME):$(IMAGE_TAG) --push .

run:
	docker run --rm -p 8081:8081 $(IMAGE_NAME):$(IMAGE_TAG)


## Apply all YAML files
apply:
	@echo " Applying manifests to namespace $(NAMESPACE)..."
	$(KUBECTL) apply -n $(NAMESPACE) -f $(ALL_MANIFESTS)

## Delete only app-related manifests (excluding DB/Ingress)
delete:
	@echo " Deleting app-related manifests in namespace $(NAMESPACE)..."
	$(KUBECTL) delete -n $(NAMESPACE) -f $(DELETE_SAFE_MANIFESTS)

## Restart the backend deployment
restart:
	@echo "Restarting hotel-booking-backend..."
	$(KUBECTL) rollout restart deployment/hotel-booking-backend -n $(NAMESPACE)

## Delete only the backend deployment
delete-deployment:
	@echo "🗑️ Deleting backend deployment only..."
	$(KUBECTL) delete deployment hotel-booking-backend -n $(NAMESPACE)

## Reapply only the backend deployment (after deleting it)
reapply-deployment: delete-deployment
	@echo "🔁 Reapplying backend deployment only..."
	$(KUBECTL) apply -n $(NAMESPACE) -f $(DEPLOYMENT_MANIFEST)

## Show pods
get:
	$(KUBECTL) get pods -n $(NAMESPACE)

## Tail logs from backend
logs:
	$(KUBECTL) logs -f deployment/hotel-booking-backend -n $(NAMESPACE)

## Describe backend deployment
describe:
	$(KUBECTL) describe deployment hotel-booking-backend -n $(NAMESPACE)

## Exec into postgres pod
exec-postgres:
	$(KUBECTL) exec -it postgres-0 -n $(NAMESPACE) -- bash

## View HPA
hpa:
	$(KUBECTL) get hpa -n $(NAMESPACE)

## View ingress
ingress:
	$(KUBECTL) get ingress -n $(NAMESPACE)

## View endpoints
endpoints:
	$(KUBECTL) get endpoints -n $(NAMESPACE)

