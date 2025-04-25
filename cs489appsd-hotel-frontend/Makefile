NAMESPACE=hotel-booking-system
IMAGE=dunky2012/cs489-hotel-frontend:latest

.PHONY: all build push deploy delete logs hpa scale get

## Build the Docker image
build:
	docker build -t $(IMAGE) .

## Push the image to Docker Hub
push:
	docker push $(IMAGE)

## Apply deployment and service
deploy:
	kubectl apply -f frontend-deployment.yaml
	kubectl apply -f frontend-hpa.yaml

## Delete frontend deployment + service + HPA
delete:
	kubectl delete -f frontend-hpa.yaml || true
	kubectl delete -f frontend-deployment.yaml || true

## View logs from a running pod
logs:
	kubectl logs -n $(NAMESPACE) -l app.kubernetes.io/instance=hotel-booking-ui --tail=100 -f

## View frontend HPA
hpa:
	kubectl get hpa -n $(NAMESPACE)

## View running frontend pods
get:
	kubectl get pods -n $(NAMESPACE) -l app.kubernetes.io/component=frontend

## Scale frontend manually
scale:
	kubectl scale deployment hotel-frontend --replicas=2 -n $(NAMESPACE)

## View ingress
ingress:
	$(KUBECTL) get ingress -n $(NAMESPACE)

## View endpoints
endpoints:
	$(KUBECTL) get endpoints -n $(NAMESPACE)
