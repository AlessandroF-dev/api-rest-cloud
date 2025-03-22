## User create body 
```
{
  "name": "Alessandro",
  "account": {
    "id": 2,
    "number": "12345678-01",
    "agency": "1234",
    "balance": 2000.55,
    "limit": 4050.65
  },
  "card": {
    "number": "xxxx xxxx xxxx 4002",
    "limit": 1000
  },
  "features": [
    {
      "icon": "https://example.com/feature-icon.png",
      "description": "A descrição detalhada da funcionalidade do serviço."
    }
  ],
  "news": [
    {
      "icon": "https://example.com/news-icon.png",
      "description": "Novidade importante para o seu serviço!"
    }
  ]
}
```

## User update body /{1}

```
{
  "name": "Alessandro",
  "account": {
    "number": "1234567-01",
    "agency": "1234",
    "balance": 2200.75,
    "limit": 5000.00  
  },
  "card": {
    "number": "xxxx xxxx xxxx 4002",
    "limit": 1200  
  },
  "features": [
    {
      "icon": "https://example.com/feature-icon-updated.png", 
      "description": "Atualização na descrição da funcionalidade do serviço." 
    }
  ],
  "news": [
    {
      "icon": "https://example.com/news-icon-updated.png", 
      "description": "Notícia atualizada com uma nova descrição!"  
    }
  ]
}
```