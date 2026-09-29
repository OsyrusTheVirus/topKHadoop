# geting parameter
param([string]$task = "")

# this working directory (as if you did pwd in linux)
$PWD = $PSScriptRoot
$IMAGE = "topk"

# each of the script commands
switch($task) {
    "build"     { 
        Write-Host "!!! Remember to have Docker Desktop open if error during build !!!"
        docker build -t $IMAGE $PWD 
    }
    "run"       {
        Write-Host "Make sure you downloaded flights.csv.bz2"
        Write-Host "Download at https://www.cs.utexas.edu/~kiat/datasets/flights.csv.bz2"
        Write-Host "When running, do the following commands: "
        Write-Host "% mvn clean package"
        Write-Host "% java -jar target/topKHadoop-0.1-SNAPSHOT-jar-with-dependencies.jar flights.csv.bz2 intermediate output "
        docker run --rm -it -v "${PWD}:/usr/${IMAGE}" -w /usr/$IMAGE $IMAGE
        }
    "clean"     {docker rmi $IMAGE}
    "images"    {docker images}
    default     { 
        Write-Host "Usage: run.ps1 <OPTION>" 
        Write-Host "OPTION can either be: " 
        Write-Host "    build  - builds docker image"
        Write-Host "    run    - run the docker image"
        Write-Host "    clean  - remove the docker image"
        Write-Host "    images - list currently made docker images"
    }
}