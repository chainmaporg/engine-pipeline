#!/bin/sh

## Author: George Zhao

#Copy the artifacts over and start the process
#Note: When folder is changing, then need to manual change

hosts=("107.181.170.169")

target=/home/release
user=root
newsdir=/home/public_repo/news/
tarname=news-local.tar


for ix in ${!hosts[*]}
do
        
    #ssh $user@${hosts[$ix]} "tar -cvf $target/$tarname $newsdir"
    scp  $user@${hosts[$ix]}:$target/$tarname .
    
done

echo "fetch is done!"
