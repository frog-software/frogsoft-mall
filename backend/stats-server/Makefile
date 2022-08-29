MVN_PROJS := action age-range gender province category prediction

.PHONY: all
all: $(MVN_PROJS) apiserver data

.PHONY: run-apiserver
run-apiserver: apiserver
	cd dist && ./apiserver

.PHONY: $(MVN_PROJS)
$(MVN_PROJS): 
	cd $@ && mvn package
	mkdir -p dist
	cp "$@/target/$@-0.0.1.jar" "dist/"
	cp "$@/output/sample.txt" "dist/$@-output.txt"

.PHONY: apiserver
apiserver:
	cd apiserver && make build
	mkdir -p dist
	cp "$@/dist/apiserver" "dist/"

.PHONY: data
data:
	mkdir -p dist
	cp data/user_log_100k.csv dist/

.PHONY: package
package: all
	tar czf dist.tar.gz dist/*

.PHONY: clean
clean:
	rm -rf dist