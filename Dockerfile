FROM postgres:latest

USER postgres
ENTRYPOINT ["pg_resetwal", "-f", "-D", "/mnt/data"]
