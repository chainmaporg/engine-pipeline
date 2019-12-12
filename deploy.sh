#!/bin/sh

## Author: George Zhao

#Copy the artifacts over and start the process
#Note: When folder is changing, then need to manual change

hosts=("107.181.170.169")

target=/home/release
user=root
tarname=news.tar

tar  --exclude='./node_modules' -cvf ../$tarname ./src/main/java/org/chainmap/content/all_extraction/news/src/

for ix in ${!hosts[*]}
do
        
        echo "scp file to ${hosts[$ix]}"
        scp ../$tarname $user@${hosts[$ix]}:$target


    echo "run command  ${cmds[$ix]} ..."
    ssh $user@${hosts[$ix]} "cd $target; tar -xvf $tarname -C cmp; cd cmp; pwd;"
done

echo "deploy is done!"
