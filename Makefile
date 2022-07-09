NAME=cloud-pos-jvm
TAG=$(shell date +%Y%m%d)

.PHONY: jvm-image
jvm-image:
	docker build -t $(NAME):$(TAG) -f docker/Dockerfile.jvm .