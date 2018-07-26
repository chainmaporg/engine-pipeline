package org.chainmap.content.parser;

import org.chainmap.content.datatype.Jobs;

import java.util.List;

/**
 * Created by xingfeiy on 7/8/18.
 */
public class JobsParser implements ObjParser<Jobs> {

    @Override
    public Jobs parse(List<String> list) {
        Jobs jobs = new Jobs();
        if(list.size() != 3) return jobs;
        jobs.company = list.get(0);
        jobs.job_title = list.get(1);
        jobs.url = list.get(2);
        jobs.category = "job";
        jobs.title = jobs.job_title;
        jobs.search_content = jobs.company + " " + jobs.job_title;
        jobs.summary = "";
        return jobs;
    }
}
