#!/bin/bash
if [ -z "${VITE_BACKEND_URL:-}" ]; then
  echo "VITE_BACKEND_URL must be set"
  exit 1
fi
find 'dist' -name '*.js' -exec sed -i -e 's,VITE_BACKEND_URL_RUNTIME_REPLACEMENT,'"$VITE_BACKEND_URL"',g' {} \;
python3 -m http.server --directory dist
