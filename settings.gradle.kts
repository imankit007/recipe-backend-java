rootProject.name = "recipe-backend-java"

include(
    "recipe-common",
    "recipe-data",
    "recipe-grpc-proto",
    "recipe-migration",
    "recipe-auth",
    "recipe-api"
)
include("recipe-gateway")