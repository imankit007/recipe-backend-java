#!/usr/bin/env sh
set -eu

MINIO_ENDPOINT="${MINIO_ENDPOINT:-http://localhost:9000}"
MINIO_ROOT_USER="${MINIO_ROOT_USER:-minioadmin}"
MINIO_ROOT_PASSWORD="${MINIO_ROOT_PASSWORD:-minioadmin}"
BUCKET="${BUCKET:-recipe-media}"
MAX_WAIT=60

echo "Waiting for MinIO at $MINIO_ENDPOINT..."

echo "Configuring mc alias..."
mc alias set local "$MINIO_ENDPOINT" "$MINIO_ROOT_USER" "$MINIO_ROOT_PASSWORD" --api S3v4

echo "Creating bucket (if not exists): $BUCKET"
mc mb --ignore-existing local/"$BUCKET"
#
#cat > /tmp/lifecycle.json <<'EOF'
#{
#  "Rules": [
#    {
#      "ID": "ExpireTempObjects",
#      "Filter": { "Prefix": "temp/" },
#      "Status": "Enabled",
#      "Expiration": { "Days": 1 }
#    },
#    {
#      "ID": "AbortMultipart",
#      "Filter": {},
#      "Status": "Enabled",
#      "AbortIncompleteMultipartUpload": { "DaysAfterInitiation": 1 }
#    }
#  ]
#}
#EOF

#echo "Importing lifecycle to bucket..."
## mc ilm import expects JSON on stdin: use input redirection
#if mc ilm import local/"$BUCKET" < /tmp/lifecycle.json; then
#  echo "Lifecycle imported via mc"
#else
#  echo "mc ilm import failed; attempting aws s3api fallback"
#  # fallback with aws cli if available
#  if command -v aws >/dev/null 2>&1; then
#    aws --endpoint-url "$MINIO_ENDPOINT" s3api put-bucket-lifecycle-configuration --bucket "$BUCKET" --lifecycle-configuration file:///tmp/lifecycle.json || true
#  fi
#fi

mc ilm rule add local/recipe-media  --prefix temp/ --expiry-days 1
mc ilm rule add local/recipe-media --prefix "" --abort-days-after-init 1


echo "MinIO initialization complete."
