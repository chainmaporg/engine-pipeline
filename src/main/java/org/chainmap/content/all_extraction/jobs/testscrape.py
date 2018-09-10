import requests
import bs4
from bs4 import BeautifulSoup
import pandas as pd
import time
from symbol import except_clause
import sys
max_results_per_city = 100;
city_set = ['New+York','Chicago','San+Francisco', 'Austin', 'Seattle', 'Los+Angeles', 'Philadelphia', 'Atlanta', 'Dallas', 'Pittsburgh', 'Portland', 'Phoenix', 'Denver', 'Houston', 'Miami', 'Washington+DC', 'Boulder']
columns = ["date", "url", "city", "job_title", "company_name", "location", "summary"]
sample_df = pd.DataFrame(columns = columns)
#scraping code:
for city in city_set:
    for start in range(0, max_results_per_city, 10):
        page = requests.get('http://www.indeed.com/jobs?q=blockchain&l=' + str(city) + '&start=' + str(start))
        time.sleep(1)  #ensuring at least 1 second between page grabs
        soup = BeautifulSoup(page.text, "lxml", from_encoding="utf-8")
        for div in soup.find_all(name="div", attrs={"class":"row"}): 
            #specifying row num for index of job posting in dataframe
            num = (len(sample_df) + 1) 
            #creating an empty list to hold the data for each posting
            job_post = []
            #grabbing date
            date = div.find_all(name="span", attrs={"class":"date"})
            if len(date) > 0:
                for span in date:
                    job_post.append(span.text)
            else:
                job_post.append("Error extracting date")
            #grabbing url
            for a in div.find_all(name="a", attrs={"data-tn-element":"jobTitle"}):
                try:
                    job_post.append(a["href"])
                except:
                    job_post.append("Error extracting link")
            print(job_post)
            #append city name
            job_post.append(city) 
            #grabbing job title
            for a in div.find_all(name="a", attrs={"data-tn-element":"jobTitle"}):
                try:
                    job_post.append(a["title"]) 
                except:
                    job_post.append("Error extracting title");
            #grabbing company name
            company = div.find_all(name="span", attrs={"class":"company"}) 
            if len(company) > 0: 
                for b in company:
                    job_post.append(b.text.strip()) 
            else: 
                sec_try = div.find_all(name="span", attrs={"class":"result-link-source"})
                if len(sec_try) > 0:
                    for span in sec_try:
                        job_post.append(span.text) 
                else:
                    job_post.append("Error extracting company")
            #grabbing location name
            c = div.findAll('span', attrs={'class': 'location'})
            c = c + div.findAll(name = 'div', attrs={'class': 'location'})
            if len(c) > 0: 
                for span in c: 
                    job_post.append(span.text) 
            else:
                job_post.append("Error extracting location")
            #grabbing summary text
            d = div.findAll('span', attrs={'class': 'summary'}) 
            if len(d) > 0: 
                for span in d:
                    job_post.append(span.text.strip()) 
            else:
                job_post.append("Error extracting summary")
            #appending list of job post info to dataframe at index num
            sample_df.loc[num] = job_post
#saving sample_df as a local csv file - define your own local path to save contents 
sample_df.to_csv("scraperesults.csv", encoding='utf-8')
sys.exit()