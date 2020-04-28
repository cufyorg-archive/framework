---
# Feel free to add content and custom Front Matter to this file.
# To modify the layout, see https://jekyllrb.com/docs/themes/#overriding-theme-defaults

layout: default
links:
  GitHub: https://github.com/cufyorg/framework
  Javadoc: https://framework.cufy.org/javadoc
  YouTube: https://youtube.com/playlist?list=PL4GvMdlkZJ6Y1SkrorANkRHArohilF2Ye
---

{{ site.description }}

<br>

{% assign pages = site.pages | sort: 'index' %}
{% for sub in pages%}

<a class="big_candy" href="{{sub.url}}">{{sub.title}}</a>
<div>
{% for link in sub.links %}
<a class="small_candy" href="{{ link[1] }}">{{ link[0] }}</a>
{% endfor %}
</div>
---

{{ sub.description }}
<br>
<br>
{% endfor %}