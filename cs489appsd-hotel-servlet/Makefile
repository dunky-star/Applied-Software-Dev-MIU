IMAGE_NAME=dunky2012/hotel-booking-app
IMAGE_TAG=1.0.1
PLATFORMS=linux/amd64,linux/arm64

.PHONY: build push run

build:
	docker buildx build --platform $(PLATFORMS) -t $(IMAGE_NAME):$(IMAGE_TAG) .

push:
	docker buildx build --platform $(PLATFORMS) -t $(IMAGE_NAME):$(IMAGE_TAG) --push .

run:
	docker run --rm -p 8081:8081 $(IMAGE_NAME):$(IMAGE_TAG)
