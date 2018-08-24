# engine-pipeline

## json-indexer

The json-indexer engine is running now, any json file under /home/public_repo will be indexed automatically.

If you have any json files want to be searchable, just need put your files under /home/public_repo, as long as the json file contains follow attributes:
- category (required)
- title (required)
- summary (required)
- url (required)
- language (optional)
- id (optional)
- tags (optional)
- date (required, please use standand date format)

Example:

{
"date": "2018-08-02 01:17:19.0",
"question_category": "Ethereum",
"challenge_id": 34,
"level": "Brozen",
"language": "english",
"title": "cryptocurrencies",
"downvote_count": 0,
"url": "http://chainmap.org/getChallengebyID/34",
"upvote_count": 0,
"search_content": "cryptocurrencies\nHow would I make my own cryptocurrency?",
"post_user_id": 39,
"category": "challenges",
"view_count": 1,
"is_closed": false
}

the json-indexer engine will scan this diretory hourly.



