package main

import (
	"context"
	"fmt"
	"github.com/aws/aws-lambda-go/events"
	"github.com/aws/aws-lambda-go/lambda"
)

func SnsHandler(ctx context.Context, snsEvent events.SNSEvent) {
	fmt.Println("BEGIN")
	record := snsEvent.Records[0]
	fmt.Printf("RECORD: %s \\n", record.SNS)
	fmt.Println("END")
}

func main() {
	lambda.Start(SnsHandler)
}
