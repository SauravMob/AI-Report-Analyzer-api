# Campaign RAG Analysis System

A Spring Boot application that provides RAG (Retrieval-Augmented Generation) capabilities for campaign performance analysis using natural language queries.

## Features

- **Natural Language Processing**: Extract campaign names and date ranges from user queries
- **Campaign Data Retrieval**: Fetch campaign reports from external API
- **AI-Powered Analysis**: Generate insights using local Ollama model (deepseek-r1:7b)
- **RESTful API**: Simple endpoint for submitting analysis queries
- **Comprehensive Error Handling**: Graceful handling of various error scenarios

## Prerequisites

1. **Java 21** or higher
2. **Maven 3.6+**
3. **Ollama** installed and running locally with deepseek-r1:7b model
4. **Campaign API** accessible with valid JWT token

## Setup Instructions

### 1. Install Ollama and Model
```bash
# Install Ollama (if not already installed)
curl -fsSL https://ollama.ai/install.sh | sh

# Pull the deepseek-r1:7b model
ollama pull deepseek-r1:7b

# Start Ollama service
ollama serve
```

### 2. Configure Application
Update `application.yml` with your settings:
```yaml
campaign:
  api:
    base-url: http://your-campaign-api-url
    bearer-token: your-jwt-token-here

ollama:
  api:
    base-url: http://localhost:11434
    model: deepseek-r1:7b
```

### 3. Build and Run
```bash
# Clone the repository
git clone <repository-url>
cd rag-analyzer

# Build the application
mvn clean package

# Run the application
mvn spring-boot:run
```

The application will start on port 8080.

## API Usage

### Analyze Campaign Performance
**POST** `/api/analyze`

**Request Body:**
```json
{
  "query": "Give me analysis for campaign 61 for last week"
}
```

**Response:**
```json
{
  "analysis": "Campaign 61 Performance Analysis...",
  "success": true,
  "error": null
}
```

### Health Check
**GET** `/api/health`

Returns service status.

## Supported Query Formats

The system can understand various natural language queries:

- `"Give me analysis for campaign 61 for last week"`
- `"Show performance of campaign_69 yesterday"`
- `"How is campaign 123 doing this month?"`
- `"Analyze campaign_45 for last month"`

### Supported Time Periods
- `today`
- `yesterday`
- `this week`
- `last week`
- `this month`
- `last month`

### Campaign Name Formats
- `campaign 61`
- `campaign_61`
- `campaign61`

## Error Handling

The system provides meaningful error messages for:
- Invalid campaign names
- Missing campaign data
- Ollama service unavailability
- Campaign API connection issues
- Invalid query formats

## Architecture

```
User Query → QueryParserService → CampaignApiService → OllamaService → Analysis Response
```

1. **QueryParserService**: Extracts campaign names and date ranges
2. **CampaignApiService**: Fetches data from external campaign API
3. **OllamaService**: Generates AI-powered analysis
4. **AnalysisService**: Orchestrates the entire flow

## Testing

Run tests with:
```bash
mvn test
```

## Troubleshooting

### Common Issues

1. **Ollama Connection Error**
    - Ensure Ollama is running: `ollama serve`
    - Verify model is installed: `ollama list`

2. **Campaign API Error**
    - Check base URL in configuration
    - Verify JWT token is valid
    - Ensure API is accessible

3. **No Campaign Data Found**
    - Verify campaign name format
    - Check if campaign exists in the specified time period
    - Confirm API response structure

## Example Queries and Responses

**Query:** `"Give me analysis for campaign 61 for last week"`

**Expected Response:**
```
Campaign 61 Performance Analysis (June 16 - June 22, 2025)

Overall Performance Assessment:
Campaign 61 showed strong performance during the last week with 245,000 impressions 
and 1,230 clicks, resulting in a solid CTR of 0.50%. The campaign generated 45 
conversions with a total spend of $892.50.

Key Metrics:
- Total Impressions: 245,000
- Total Clicks: 1,230
- Total Conversions: 45
- Conversion Rate: 3.66%
- Cost per Conversion: $19.83
- Average CTR: 0.50%

Recommendations:
1. The conversion rate of 3.66% is above industry average, indicating good targeting
2. Consider increasing budget to scale successful performance
3. Monitor CTR trends and optimize creative if it starts declining
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request