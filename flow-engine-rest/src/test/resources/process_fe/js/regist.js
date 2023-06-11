const ports = {
    groups: {
      top: {
        position: 'top',
        attrs: {
          circle: {
            r: 4,
            magnet: true,
            stroke: '#5F95FF',
            strokeWidth: 1,
            fill: '#fff',
            style: {
              visibility: 'hidden',
            },
          },
        },
      },
      right: {
        position: 'right',
        attrs: {
          circle: {
            r: 4,
            magnet: true,
            stroke: '#5F95FF',
            strokeWidth: 1,
            fill: '#fff',
            style: {
              visibility: 'hidden',
            },
          },
        },
      },
      bottom: {
        position: 'bottom',
        attrs: {
          circle: {
            r: 4,
            magnet: true,
            stroke: '#5F95FF',
            strokeWidth: 1,
            fill: '#fff',
            style: {
              visibility: 'hidden',
            },
          },
        },
      },
      left: {
        position: 'left',
        attrs: {
          circle: {
            r: 4,
            magnet: true,
            stroke: '#5F95FF',
            strokeWidth: 1,
            fill: '#fff',
            style: {
              visibility: 'hidden',
            },
          },
        },
      },
    },
    items: [
      {
        group: 'top',
      },
      {
        group: 'right',
      },
      {
        group: 'bottom',
      },
      {
        group: 'left',
      },
    ],
  }
              X6.Graph.registerNode(
    'custom-rect',
    {
      inherit: 'rect',
      width: 100,
      height: 50,
      attrs: {
        body: {
          strokeWidth: 1,
          stroke: '#5F95FF',
          fill: '#EFF4FF',
        },
        text: {
          fontSize: 12,
          fill: '#262626',
        },
      },
      ports: { ...ports },
    },
    true,
  )
  
  X6.Graph.registerNode(
    'custom-polygon',
    {
      inherit: 'polygon',
      width: 200,
      height: 70,
      attrs: {
        body: {
          strokeWidth: 1,
          stroke: '#5F95FF',
          fill: '#EFF4FF',
        },
        text: {
          fontSize: 12,
          fill: '#262626',
        },
      },
      ports: {
        ...ports,
        items: [
          {
            group: 'top',
          },
          {
            group: 'bottom',
          },
        ],
      },
    },
    true,
  )
  
  X6.Graph.registerNode(
    'custom-circle',
    {
      inherit: 'circle',
      width: 100,
      height: 100,
      attrs: {
        body: {
          strokeWidth: 1,
          stroke: '#5F95FF',
          fill: '#EFF4FF',
        },
        text: {
          fontSize: 12,
          fill: '#262626',
        },
      },
      ports: { ...ports },
    },
    true,
  )
  
  X6.Graph.registerNode(
    'custom-image',
    {
      inherit: 'rect',
      width: 100,
      height: 100,
      markup: [
        {
          tagName: 'rect',
          selector: 'body',
        },
        {
          tagName: 'image',
        },
        {
          tagName: 'text',
          selector: 'label',
        },
      ],
      attrs: {
        body: {
          stroke: '#5F95FF',
          fill: '#5F95FF',
        },
        image: {
          width: 26,
          height: 26,
          refX: 13,
          refY: 16,
        },
        label: {
          refX: 3,
          refY: 2,
          textAnchor: 'left',
          textVerticalAnchor: 'top',
          fontSize: 12,
          fill: '#fff',
        },
      },
      ports: { ...ports },
    },
    true,
  )


const data = {
    nodes:[],
    edges:[]
}

for(const element of myflow.elements){
    if(element.type=='START'){
        const node = {
            id: element.id,
            label: element.name,
            x: 40,
            y: 40,
            width: 150,
            height: 60,
            shape: 'ellipse'
        }
        data.nodes.push(node)
    }else if(element.type=='END'){
        const node = {
            id: element.id,
            label: element.name,
            shape: 'ellipse',
            width: 150,
            height: 60
        }
        data.nodes.push(node)
    }else if(element.type=='DECISION'){
        const node = {
            id: element.id,
            label:element.name,
            shape: 'custom-polygon',
            attrs: {
                body: {
                    refPoints: '0,10 10,0 20,10 10,20'
                }
            }
        }
        data.nodes.push(node)
    }else if(element.type=='SERVICE_TASK'){
        const node = {
            id: element.id,
            label: element.name,
            width: 200,
            height: 60,
            attrs:{}
        }
        if(element.config?.manual==true){
            node.attrs.body = {fill: '#FFD700'}
        }
        data.nodes.push(node)
    }else if(element.type=='USER_TASK'){
        const node = {
            id: element.id,
            label: element.name,
            width: 200,
            height: 60,
            attrs:{}
        }
        node.attrs.body = {fill: '#FFD700'}
        data.nodes.push(node)
    }else if(element.type=='CALL_ACTIVITY'){
        const node = {
            id: element.id,
            label:element.name,
            shape: 'custom-polygon',
            attrs: {
                body: {
                    refPoints: '10,0 40,0 30,20 0,20'
                }
            }
        }
        data.nodes.push(node)
    }else if(element.type=='SEQUENCE_FLOW'){
        const edge = {
            source: element.sourceId,
            target: element.targetId,
            labels:[]
        }
        if(element.config?.condition!=null){
            edge.labels.push(element.config.condition)
        }
        data.edges.push(edge)
    }
}