FROM quay.io/stamen/toner:v1.1.0

USER root

RUN \
  apt-get install -y git joe

RUN \
  add-apt-repository -r ppa:ubuntugis/ubuntugis-unstable && \
  add-apt-repository ppa:ubuntugis/ppa && \
  apt-get update && \
  apt-get install -y libgeos-c1v5 && \
  apt-get install -y autoconf build-essential cmake docbook-mathml \
    docbook-xsl libboost-dev libboost-filesystem-dev libboost-timer-dev \
    libcgal-dev libcunit1-dev libgdal-dev libgeos++-dev libgeotiff-dev \
    libgmp-dev libjson0-dev libjson-c-dev liblas-dev libmpfr-dev \
    libopenscenegraph-dev libpq-dev libproj-dev libxml2-dev \
    xsltproc git build-essential wget && \
  apt-get -y install mapnik-utils gdal-bin && \
  apt-get install -y postgresql-server-dev-9.3 && \
  apt-get install -y postgresql && \
  apt-get install -y postgresql-contrib-9.3 && \
  apt-get install -y postgresql-9.3-postgis-2.2

USER postgres
RUN \
  /etc/init.d/postgresql restart && \
  psql -c "CREATE USER toner WITH SUPERUSER PASSWORD 'toner';" && \
  echo "host all  all    0.0.0.0/0  md5" >> /etc/postgresql/9.3/main/pg_hba.conf && \
  echo "listen_addresses='*'" >> /etc/postgresql/9.3/main/postgresql.conf

USER root

ENV DATABASE_URL "postgres://toner:toner@localhost/toner"

RUN \
  mkdir /install && \
  cd /install/ && \
  wget http://imposm.org/static/rel/imposm3-0.1dev-20140811-3f3c12e-linux-x86-64.tar.gz && \
  tar xvf imposm3-0.1dev-20140811-3f3c12e-linux-x86-64.tar.gz && \
  mv imposm3-0.1dev-20140811-3f3c12e-linux-x86-64 imposm3 && \
  echo 'PATH=$PATH:/install/imposm3' >> ~/.bashrc && \
  git clone https://github.com/vanjakom/toner-carto.git && \
  cd toner-carto && \
  npm install pgexplode && \
  echo 'DATABASE_URL=postgres://toner:toner@localhost/toner' > .env

WORKDIR /install/toner-carto

COPY start.sh /install/start.sh

ENTRYPOINT ["/install/start.sh"]
