build:
	docker build -t nginx-vts .

run:
	docker run -it -p 8000:8000 nginx-vts
